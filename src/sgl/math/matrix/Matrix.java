/*
 * The MIT License
 *
 * Copyright 2016 link.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * Created file on 10/2/16 at 4:58 PM.
 *
 * This file is part of XUGL
 */
package sgl.math.matrix;

/**
 * @author link
 */
public interface Matrix<T extends Comparable<T>> extends Comparable<Matrix<T>> {

	T[] toArray();

	boolean[] toArrayB();

	byte[] toArrayb();

	short[] toArrays();

	int[] toArrayi();

	long[] toArrayl();

	float[] toArrayf();

	double[] toArrayd();

	Matrix<T> add(Matrix<T> matrix);

	Matrix<T> sub(Matrix<T> matrix);

	Matrix<T> mul(Matrix<T> matrix);

	Matrix<T> div(Matrix<T> matrix);

	Matrix<T> invert();

}
