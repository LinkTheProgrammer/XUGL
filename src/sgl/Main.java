/*
 * The MIT License
 *
 * Copyright 2015 link.
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
 * Created file on 12/27/15 at 4:08 PM.
 *
 * This file is part of jGUI
 */

package sgl;

import sgl.gl.canvas.GL11Canvas;
import sgl.glfw.log.GLFWLogger;
import sgl.glfw.ui.display.GLFWDisplay;
import sgl.io.Keyboard;
import sgl.log.Logger;
import sgl.ui.canvas.Canvas;
import sgl.ui.display.Display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author link
 */
public class Main {

	private static final Logger LOGGER = new GLFWLogger("Main Logger");

	public static void main(String... args) {

		LOGGER.log("Started XUGL Test");

		Display display = new GLFWDisplay(0, 0, 800, 600, "GLFW Display", new GL11Canvas());
		Keyboard keyboard = display.getKeyboard();
		Canvas canvas = display.getCanvas();
		display.show();

		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		while (!glfwWindowShouldClose(((GLFWDisplay) display).ptr())) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(((GLFWDisplay) display).ptr());
			glfwPollEvents();

			display.refresh();
			if (keyboard.isKeyTyped(GLFW_KEY_ESCAPE)) {
				display.close();
			} else if (keyboard.isKeyTyped(GLFW_KEY_R)) {
				display.refresh();
			}
		}
	}

	public static double avg(double[] values) {
		double result = 0;

		for (double value : values) {
			result += value;
		}

		result /= values.length;

		return result;
	}

}