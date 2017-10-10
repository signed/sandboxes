/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package sample;

/**
 *
 *
 * lets see if this is dumped twice
 *
 *
 *
 * Should trim the empty lines between text
 *
 * @author blub
 * @since 2012/07/15
 *
 * @apiNote just to generate some tow line empty space in the middle of the doc
 *
 *
 */
public class JavaDocOnClass {

    /*
     * Another block comment
     */
    public void foo(int e) {
    }

    /**
     * @since 2014/07/15
     */
    public void bar(){

    }

    /**
     * Some text
     *
     * @author banana
     *
     */
    public void baz(){

    }

    /**
     * Some Test
     *
     * @author banana
     * @since today
     */

    public void quox(){

    }
}
