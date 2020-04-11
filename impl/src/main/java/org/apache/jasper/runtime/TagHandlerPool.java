/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.runtime;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.JspTag;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.ServletConfig;
import org.apache.jasper.Constants;
import org.glassfish.jsp.api.ResourceInjector;

/**
 * Pool of tag handlers that can be reused.
 *
 * @author Jan Luehe
 */
public class TagHandlerPool {

    public static final String OPTION_TAGPOOL = "tagpoolClassName";
    public static final String OPTION_MAXSIZE = "tagpoolMaxSize";

    private JspTag[] handlers;
    private ResourceInjector resourceInjector;

    // index of next available tag handler
    private int current;

    public static TagHandlerPool getTagHandlerPool(ServletConfig config) {
        TagHandlerPool result = null;

        String tpClassName = getOption(config, OPTION_TAGPOOL, null);
        if (tpClassName != null) {
            try {
                Class<? extends TagHandlerPool> c = Class.forName(tpClassName).asSubclass(TagHandlerPool.class);
                result = c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                result = null;
            }
        }
        if (result == null)
            result = new TagHandlerPool();
        result.init(config);

        return result;
    }

    protected void init(ServletConfig config) {
        int maxSize = -1;
        String maxSizeS = getOption(config, OPTION_MAXSIZE, null);
        if (maxSizeS != null) {
            try {
                maxSize = Integer.parseInt(maxSizeS);
            } catch (Exception ex) {
                maxSize = -1;
            }
        }
        if (maxSize < 0) {
            maxSize = Constants.MAX_POOL_SIZE;
        }
        this.handlers = new JspTag[maxSize];
        this.current = -1;

        this.resourceInjector = (ResourceInjector) config.getServletContext().getAttribute(Constants.JSP_RESOURCE_INJECTOR_CONTEXT_ATTRIBUTE);
    }

    /**
     * Constructs a tag handler pool with the default capacity.
     */
    public TagHandlerPool() {
        // Nothing - jasper generated servlets call the other constructor,
        // this should be used in future + init .
    }

    /**
     * Constructs a tag handler pool with the given capacity.
     *
     * @param capacity Tag handler pool capacity
     * @deprecated Use static getTagHandlerPool
     */
    public TagHandlerPool(int capacity) {
        this.handlers = new JspTag[capacity];
        this.current = -1;
    }

    /**
     * Gets the next available tag handler from this tag handler pool, instantiating one if this tag handler pool is empty.
     *
     * @param handlerClass Tag handler class
     *
     * @return Reused or newly instantiated tag handler
     *
     * @throws JspException if a tag handler cannot be instantiated
     */
    public <T extends JspTag> JspTag get(Class<T> handlerClass) throws JspException {
        synchronized (this) {
            if (current >= 0) {
                return handlers[current--];
            }
        }

        // Out of sync block - there is no need for other threads to
        // wait for us to construct a tag for this thread.
        JspTag tagHandler = null;
        try {
            if (resourceInjector != null) {
                tagHandler = resourceInjector.createTagHandlerInstance(handlerClass);
            } else {
                tagHandler = handlerClass.newInstance();
            }
        } catch (Exception e) {
            throw new JspException(e.getMessage(), e);
        }

        return tagHandler;
    }

    /**
     * Adds the given tag handler to this tag handler pool, unless this tag handler pool has already reached its capacity,
     * in which case the tag handler's release() method is called.
     *
     * @param handler JspTag handler to add to this tag handler pool
     */
    public void reuse(JspTag handler) {
        synchronized (this) {
            if (current < (handlers.length - 1)) {
                handlers[++current] = handler;
                return;
            }
        }
        // There is no need for other threads to wait for us to release
        if (handler instanceof Tag) {
            ((Tag) handler).release();
        }

        if (resourceInjector != null) {
            resourceInjector.preDestroy(handler);
        }
    }

    /**
     * Calls the release() method of all available tag handlers in this tag handler pool.
     */
    public synchronized void release() {
        for (int i = current; i >= 0; i--) {
            if (handlers[i] instanceof Tag) {
                ((Tag) handlers[i]).release();
            }
            if (resourceInjector != null) {
                resourceInjector.preDestroy(handlers[i]);
            }
        }
    }

    protected static String getOption(ServletConfig config, String name, String defaultV) {
        if (config == null)
            return defaultV;

        String value = config.getInitParameter(name);
        if (value != null)
            return value;
        if (config.getServletContext() == null)
            return defaultV;
        value = config.getServletContext().getInitParameter(name);
        if (value != null)
            return value;
        return defaultV;
    }

}
