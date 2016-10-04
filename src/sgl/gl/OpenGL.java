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
 * Created file on 5/26/16 at 9:07 PM.
 *
 * This file is part of XUGL
 */
package sgl.gl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import sgl.gl.context.GLContext;
import sgl.gl.image.GLImage;
import sgl.gl.profile.GLProfile;
import sgl.ui.display.Display;
import sgl.ui.image.color.Color;
import sun.reflect.CallerSensitive;

import java.util.HashSet;
import java.util.Set;

import static sgl.gl.OpenGL.Feature.*;

/**
 * A very high level OpenGL decoupling to make your life easier.
 * <p>
 * This provided OpenGL decoupling provides a version and feature set, as
 * well
 * as GL profiles. Each GL profile contains the same methods as this class, and
 * each is required to implement them for the appropriate OpenGL context
 * Most parameters are preserved.
 * </p>
 * <p>
 * Proper usage of this class is vital. One may prefer using this decoupling in
 * preference to directly calling LWJGL bindings. To ensure that this class and
 * OpenGl work correctly with your application:
 * <ol>
 * <li>Ensure an OpenGL context is current for the current thread. This is
 * usually done by creating a window, but the {@linkplain
 * sgl.gl.context.GLContext GLContext} ctor without parameters allows you to
 * create a headless OpenGL context for the current thread by creating a
 * {@link Display} object for you (this object is destroyed with the
 * context).</li>
 * <li>Choose a profile from GLProfile using {@code GLProfile.forVersion()}
 * or, for the latest available core profile, use {@link GLProfile#CORE}, and
 * call
 * {@code OpenGL.setProfile(profile)} with the chosen profile.</li>
 * <li>Call {@code OpenGL.initialize(GLContext)} with the GLContext object you
 * created. <strong><em>This must happen after you initialize the Display
 * object.</em></strong></li>
 * </ol>
 * </p>
 * <p>
 * The resulting code might look like this pseudo-code:
 * <pre>
 *      GLDisplay display = new GLFWDisplay(x, y, width, height, title, canvas);
 *      OpenGL.setProfile(display.getContext().getProfile());
 *      OpenGL.initialize(display.getContext());
 * </pre>
 * </p>
 *
 * @author link
 */
public enum OpenGL {
	;

	private static volatile GLProfile profile;
	private static volatile GLContext context;

	private static final Set<Integer> VERSIONS = new HashSet<>();
	private static final Set<Feature> FEATURES = new HashSet<>();
	private static final int VERSION;


	/**
	 * Full list of OpenGL Features and Extensions
	 */
	public enum Feature {

		// AMD

		AMD_BLEND_MIN_MAX_FACTOR,
		AMD_DEBUG_OUTPUT,
		AMD_DEPTH_CLAMP_SEPERATE,
		AMD_DRAW_BUFFERS_BLEND,
		AMD_GPU_SHADER_INT_64,
		AMD_INTERLEAVED_ELEMENTS,
		AMD_OCCLUSION_QUERY_EVENT,
		AMD_PERFORMANCE_MONITOR,
		AMD_PINNED_MEMORY,
		AMD_QUERY_BUFFER_OBJECT,
		AMD_SAMPLE_POSITIONS,
		AMD_SEAMLESS_CUBEMAP_PER_TEXTURE,
		AMD_SPARSE_TEXTURE,
		AMD_STENCIL_OPERATION_EXTENDED,
		AMD_TRANSFORM_FEEDBACK_4,
		AMD_VERTEX_SHADER_TESSELLATOR,

		// ARB

		ARB_ARRAY_OF_ARRAYS,
		ARB_BASE_INSTANCE,
		ARB_BINDLESS_TEXTURE,
		ARB_BLEND_FUNC_EXTENDED,
		ARB_BUFFER_STORAGE,
		ARB_CLEAR_BUFFER_OBJECT,
		ARB_CLEAR_TEXTURE,
		ARB_CL_EVENT,
		ARB_CLIP_CONTROL,
		ARB_COLOR_BUFFER_FLOAT,
		ARB_COMPRESSED_TEXTURE_PIXEL_STORAGE,
		ARB_COMPUTE_SHADER,
		ARB_COMPUTE_VARIABLE_GROUP_SIZE,
		ARB_CONDITIONAL_RENDER_INVERTED,
		ARB_CONSERVATIVE_DEPTH,
		ARB_COPY_BUFFER,
		ARB_COPY_IMAGE,
		ARB_CULL_DISTANCE,
		ARB_DEBUG_GROUP,
		ARB_DEBUG_LABEL,
		ARB_DEBUG_OUTPUT2,
		ARB_DEBUG_OUTPUT,
		ARB_DEPTH_BUFFER_FLOAT,
		ARB_DEPTH_CLAMP,
		ARB_DEPTH_TEXTURE,
		ARB_DERIVATIVE_CONTROL,
		ARB_DIRECT_STATE_ACCESS,
		ARB_DRAW_BUFFERS,
		ARB_DRAW_BUFFERS_BLEND,
		ARB_DRAW_ELEMENTS_BASE_VERTEX,
		ARB_DRAW_INDIRECT,
		ARB_DRAW_INSTANCED,
		ARB_ENHANCED_LAYOUTS,
		ARB_ES2_COMPATIBILITY,
		ARB_ES3_COMPATIBILITY,
		ARB_ES31_COMPATIBILITY,
		ARB_ES32_COMPATIBILITY,
		ARB_EXPLICIT_ATTRIB_LOCATION,
		ARB_EXPLICIT_UNIFORM_LOCATION,
		ARB_FRAGMENT_COORD_CONVENTIONS,
		ARB_FRAGMENT_LAYER_VIEWPORT,
		ARB_FRAGMENT_PROGRAM,
		ARB_FRAGMENT_SHADER,
		ARB_FRAMEBUFFER_NO_ATTACHMENTS,
		ARB_FRAMEBUFFER_OBJECT,
		ARB_FRAMEBUFFER_SRGB,
		ARB_GEOMETRY_SHADER4,
		ARB_GET_PROGRAM_BINARY,
		ARB_GET_TEXTURE_SUB_IMAGE,
		ARB_GPU_SHADER5,
		ARB_GPU_SHADER_FP64,
		ARB_GPU_SHADER_INT64,
		ARB_HALF_FLOAT_PIXEL,
		ARB_HALF_FLOAT_VERTEX,
		ARB_IMAGING,
		ARB_INDIRECT_PARAMETERS,
		ARB_INSTANCED_ARRAYS,
		ARB_INTERNALFORMAT_QUERY,
		ARB_INTERNALFORMAT_QUERY2,
		ARB_INVALIDATE_SUBDATA,
		ARB_MAP_BUFFER_ALIGNMENT,
		ARB_MAP_BUFFER_RANGE,
		ARB_MATRIX_PALETTE,
		ARB_MULTI_BIND,
		ARB_MULTI_DRAW_INDIRECT,
		ARB_MULTISAMPLE,
		ARB_MULTITEXTURE,
		ARB_OCCLUSION_QUERY,
		ARB_OCCLUSION_QUERY2,
		ARB_PARALLEL_SHADER_COMPILE,
		ARB_PIPELINE, STATISTICS_QUERY,
		ARB_PIXEL_BUFFER_OBJECT,
		ARB_POINT_PARAMETERS,
		ARB_POINT_SPRITE,
		ARB_PROGRAM_INTERFACE_QUERY,
		ARB_PROVOKING_VERTEX,
		ARB_QUERY_BUFFER_OBJECT,
		ARB_ROBUSTNESS,
		ARB_ROBUST_BUFFER_ACCESS_BEHAVIOR,
		ARB_SAMPLE_LOCATIONS,
		ARB_SAMPLER_OBJECTS,
		ARB_SAMPLE_SHADING,
		ARB_SEAMLESS_CUBE_MAP,
		ARB_SEAMLESS_CUBEMAP_PER_TEXTURE,
		ARB_SEPARATE_SHADER_OBJECTS,
		ARB_SHADER_ATOMIC_COUNTERS,
		ARB_SHADER_BIT_ENCODING,
		ARB_SHADER_IMAGE_LOAD_STORE,
		ARB_SHADER_IMAGE_SIZE,
		ARB_SHADER_OBJECTS,
		ARB_SHADER_PRECISION,
		ARB_SHADER_STORAGE_BUFFER_OBJECT,
		ARB_SHADER_SUBROUTINE,
		ARB_SHADER_TEXTURE_IMAGE_SAMPLES,
		ARB_SHADING_LANGUAGE_100,
		ARB_SHADING_LANGUAGE_420PACK,
		ARB_SHADING_LANGUAGE_INCLUDE,
		ARB_SHADOW,
		ARB_SHADOW_AMBIENT,
		ARB_SPARSE_BUFFER,
		ARB_SPARSE_TEXTURE,
		ARB_STENCIL_TEXTURING,
		ARB_SYNC,
		ARB_TESSELLATION_SHADER,
		ARB_TEXTURE_BARRIER,
		ARB_TEXTURE_BORDER_CLAMP,
		ARB_TEXTURE_BUFFER_OBJECT,
		ARB_TEXTURE_BUFFER_OBJECT_RGB32,
		ARB_TEXTURE_BUFFER_RANGE,
		ARB_TEXTURE_COMPRESSION,
		ARB_TEXTURE_COMPRESSION_BPTC,
		ARB_TEXTURE_COMPRESSION_RGTC,
		ARB_TEXTURE_CUBE_MAP,
		ARB_TEXTURE_CUBE_MAP_ARRAY,
		ARB_TEXTURE_ENV_ADD,
		ARB_TEXTURE_ENV_COMBINE,
		ARB_TEXTURE_ENV_CROSSBAR,
		ARB_TEXTURE_ENV_DOT3,
		ARB_TEXTURE_FILTER_MINMAX,
		ARB_TEXTURE_FLOAT,
		ARB_TEXTURE_GATHER,
		ARB_TEXTURE_MIRROR_CLAMP_TO_EDGE,
		ARB_TEXTURE_MIRRORED_REPEAT,
		ARB_TEXTURE_MULTISAMPLE,
		ARB_TEXTURE_NON_POWER_OF_TWO,
		ARB_TEXTURE_QUERY_LEVELS,
		ARB_TEXTURE_QUERY_LOD,
		ARB_TEXTURE_RECTANGLE,
		ARB_TEXTURE_RG,
		ARB_TEXTURE_RGB10_A2UI,
		ARB_TEXTURE_STENCIL8,
		ARB_TEXTURE_STORAGE,
		ARB_TEXTURE_STORAGE_MULTISAMPLE,
		ARB_TEXTURE_SWIZZLE,
		ARB_TEXTURE_VIEW,
		ARB_TIMER_QUERY,
		ARB_TRANSFORM_FEEDBACK2,
		ARB_TRANSFORM_FEEDBACK3,
		ARB_TRANSFORM_FEEDBACK_INSTANCED,
		ARB_TRANSFORM_FEEDBACK_OVERFLOW_QUERY,
		ARB_TRANSPOSE_MATRIX,
		ARB_UNIFORM_BUFFER_OBJECT,
		ARB_VERTEX_ARRAY_BGRA,
		ARB_VERTEX_ARRAY_OBJECT,
		ARB_VERTEX_ATTRIB_64BIT,
		ARB_VERTEX_ATTRIB_BINDING,
		ARB_VERTEX_BLEND,
		ARB_VERTEX_BUFFER_OBJECT,
		ARB_VERTEX_PROGRAM,
		ARB_VERTEX_SHADER,
		ARB_VERTEX_TYPE_2_10_10_10_REV,
		ARB_VERTEX_TYPE_10F_11F_11F_REV,
		ARB_VIEWPORT_ARRAY,
		ARB_WINDOW_POS,

		// ATI

		ATI_MEM_INFO,
		ATI_SEPARATE_STENCIL,
		ATI_TEXTURE_COMPRESSION_3DC,

		CGL,

		// Apple

		APPLE_FLUSH_BUFFER_RANGE,
		APPLE_VERTEX_ARRAY_OBJECT,

		// EXT

		EXT_422_PIXELS,
		EXT_ABGR,
		EXT_BGRA,
		EXT_BINDABLE_UNIFORM,
		EXT_BLEND_LOGIC_OP,
		EXT_BLEND_COLOR,
		EXT_BLEND_FUNC_SEPARATE,
		EXT_BLEND_EQUATION_SEPARATE,
		EXT_FOG_COORD,
		EXT_FUNC_SEPARATE,
		EXT_BLEND_MINMAX,
		EXT_BLEND_SUBTRACT,
		EXT_CLIP_VOLUME_HINT,
		EXT_COLOR_TABLE,
		EXT_COLOR_SUBTABLE,
		EXT_COMPILED_VERTEX_ARRAY,
		EXT_CONVOLUTION,
		EXT_COPY_TEXTURE,
		EXT_DEBUG_LABEL,
		EXT_DEBUG_MARKER,
		EXT_DEPTH_BOUNDS_TEST,
		EXT_DIRECT_STATE_ACCESS,
		EXT_DRAW_BUFFERS2,
		EXT_DRAW_INSTANCED,
		EXT_DRAW_RANGE_ELEMENTS,
		EXT_FRAMEBUFFER_BLIT,
		EXT_FRAMEBUFFER_MULTISAMPLE,
		EXT_FRAMEBUFFER_MULTISAMPLE_BLIT_SCALED,
		EXT_FRAMEBUFFER_OBJECT,
		EXT_FRAMEBUFFER_SRGB,
		EXT_GEOMETRY_SHADER4,
		EXT_GPU_PROGRAM_PARAMETERS,
		EXT_GPU_SHADER4,
		EXT_HISTOGRAM,
		EXT_MULTI_DRAW_ARRAYS,
		EXT_PACKED_DEPTH_STENCIL,
		EXT_PACKED_FLOAT,
		EXT_PACKED_PIXELS,
		EXT_PIXEL_BUFFER_OBJECT,
		EXT_POINT_PARAMETERS,
		EXT_POLYGON_OFFSET,
		EXT_POLYGON_OFFSET_CLAMP,
		EXT_PROVOKING_VERTEX,
		EXT_RASTER_MULTISAMPLE,
		EXT_RESCALE_NORMAL,
		EXT_SECONDARY_COLOR,
		EXT_SEPARATE_SHADER_OBJECTS,
		EXT_SEPARATE_SPECULAR_COLOR,
		EXT_SHADER_IMAGE_LOAD_STORE,
		EXT_SHARED_TEXTURE_PALETTE,
		EXT_SHADOW_FUNCS,
		EXT_STENCIL_CLEAR_TAG,
		EXT_STENCIL_TWO_SIDE,
		EXT_STENCIL_WRAP,
		EXT_SUBTEXTURE,
		EXT_TEXTURE,
		EXT_TEXTURE3D,
		EXT_TEXTURE_ARRAY,
		EXT_TEXTURE_BUFFER_OBJECT,
		EXT_TEXTURE_COMPRESSION_LATC,
		EXT_TEXTURE_COMPRESSION_RGTC,
		EXT_TEXTURE_COMPRESSION_S3TC,
		EXT_TEXTURE_FILTER_ANISOTROPIC,
		EXT_TEXTURE_FILTER_MINMAX,
		EXT_TEXTURE_INTEGER,
		EXT_TEXTURE_LOD_BIAS,
		EXT_TEXTURE_MIRROR_CLAMP,
		EXT_TEXTURE_OBJECT,
		EXT_TEXTURE_SHARED_EXPONENT,
		EXT_TEXTURE_S_NORM,
		EXT_TEXTURE_SRGB,
		EXT_TEXTURE_SRGB_DECODE,
		EXT_TEXTURE_SWIZZLE,
		EXT_TIMER_QUERY,
		EXT_TRANSFORM_FEEDBACK,
		EXT_VERTEX_ARRAY,
		EXT_VERTEX_ATTRIB_64BIT,
		EXT_X11_SYNC_OBJECT,

		// GL SGI

		SGI_COLOR_MATRIX,

		SGIS_GENERATE_MIPMAP,
		SGIS_TEXTURE_EDGE_CLAMP,
		SGIS_TEXTURE_LOAD,

		// HP

		HP_CONVOLUTION_BORDER_MODES,

		// GLX

		GLX_AMD_GPU_ASSOCIATION,

		GLX_ARB_CONTEXT_FLUSH_CONTROL,
		GLX_ARB_CREATE_CONTEXT,
		GLX_ARB_CREATE_CONTEXT_PROFILE,
		GLX_ARB_CREATE_CONTEXT_ROBUSTNESS,
		GLX_ARB_BFB_CONFIG_FLOAT,
		GLX_ARB_FRAMEBUFFER_SRGB,
		GLX_ARB_GET_PROC_ADDRESS,
		GLX_ARB_MULTISAMPLE,
		GLX_ARB_ROBUSTNESS_APPLICATION_ISOLATION,
		GLX_ARB_VERTEX_BUFFER_OBJECT,

		// GLX EXT

		GLX_EXT_BUFFER_AGE,
		GLX_EXT_CREATE_CONTEXT_ES2_PROFILE,
		GLX_EXT_CREATE_CONTEXT_ES_PROFILE,
		GLX_EXT_FB_CONFIG_PACKED_FLOAT,
		GLX_EXT_FRAMEBUFFER_SRGB,
		GLX_EXT_IMPORT_CONTEXT,
		GLX_EXT_STEREO_TREE,
		GLX_EXT_SWAP_CONTROL,
		GLX_EXT_SWAP_CONTROL_TEAR,
		GLX_EXT_TEXTURE_FROM_PIXMAP,
		GLX_EXT_VISUAL_INFO,
		GLX_EXT_VISUAL_RATING,

		// GLX INTEL

		GLX_INTEL_SWAP_EVENT,

		// GLX NV

		GLX_NV_COPY_BUFFER,
		GLX_NV_COPY_IMAGE,
		GLX_NV_DELAY_BEFORE_SWAP,
		GLX_NV_FLOAT_BUFFER,
		GLX_NV_MULTISAMPLE_COVERAGE,
		GLX_NV_SWAP_GROUP,

		// GLX SGI(X)

		GLX_SGI_MAKE_CURRENT_READ,
		GLX_SGI_SWAP_CONTROL,
		GLX_SGI_VIDEO_SYNC,
		GLX_SGIX_FB_CONFIG,
		GLX_SGIX_PBUFFER,
		GLX_SGIX_SWAP_BARRIER,
		GLX_SGIX_SWAP_GROUP,

		GLX_EXT_STEREO_NOTIFY_EVENT,

		// INTEL

		INTEL_FRAMEBUFFER_CMAA,
		INTEL_MAP_TEXTURE,
		INTEL_PERFORMANCE_QUERY,

		// KHR

		KHR_BLEND_EQUATION_ADVANCED,
		KHR_BLEND_EQUATION_ADVANCED_COHERENT,
		KHR_CONTEXT_FLUSH_CONTROL,
		KHR_DEBUG,
		KHR_NO_ERROR,
		KHR_ROBUSTNESS,
		KHR_TEXTURE_COMPRESSION_ASTCLDR,

		// NV

		NV_BINDLESS_MULTI_DRAW_INDIRECT,
		NV_BINDLESS_MULTI_DRAW_INDIRECT_COUNT,
		NV_BINDLESS_TEXTURE,
		NV_BLEND_EQUATION_ADVANCED,
		NV_BLEND_EQUATION_ADVANCED_COHERENT,
		NV_BLEND_SQUARE,
		NV_COMMAND_LIST,
		NV_CONDITIONAL_RENDER,
		NV_CONSERVATIVE_RASTER,
		NV_CONSERVATIVE_RASTER_DILATE,
		NV_COPY_DEPTH_TO_COLOR,
		NV_COPY_IMAGE,
		NV_DEEP_TEXTURE_3D,
		NV_DEPTH_BUFFER_FLOAT,
		NV_DEPTH_CLAMP,
		NV_DRAW_TEXTURE,
		NV_DRAW_VULKAN_IMAGE,
		NV_EXPLICIT_MULTISAMPLE,
		NV_FENCE,
		NV_FILL_RECTANGLE,
		NV_FLOAT_BUFFER,
		NV_FOG_DISTANCE,
		NV_FRAGMENT_COVERAGE_TO_COLOR,
		NV_FRAMEBUFFER_MIXED_SAMPLES,
		NV_FRAMEBUFFER_MULTISAMPLE_COVERAGE,
		NV_GPU_SHADER5,
		NV_HALF_FLOAT,
		NV_INTERNAL_FORMAT_SAMPLE_QUERY,
		NV_LIGHT_MAX_EXPONENT,
		NV_MULTISAMPLE_COVERAGE,
		NV_MULTISAMPLE_FILTER_HINT,
		NV_PACKED_DEPTH_STENCIL,
		NV_PATH_RENDERING,
		NV_PATH_RENDERING_SHARED_EDGE,
		NV_PIXEL_DATA_RANGE,
		NV_POINT_SPRITE,
		NV_PRIMITIVE_RESTART,
		NV_SAMPLE_LOCATIONS,
		NV_SHADER_BUFFER_LOAD,
		NV_SHADER_BUFFER_STORE,
		NV_SHADER_THREAD_GROUP,
		NV_TEXGEN_REFLECTION,
		NV_TEXTURE_BARRIER,
		NV_TEXTURE_MULTISAMPLE,
		NV_TRANSFORM_FEEDBACK,
		NV_TRANSFORM_FEEDBACK2,
		NV_UNIFORM_BUFFER_UNIFIED_MEMORY,
		NV_VERTEX_ARRAY_RANGE,
		NV_VERTEX_ARRAY_RANGE2,
		NV_VERTEX_ATTRIB_INTEGER_64BIT,
		NV_VERTEX_BUFFER_UNIFIED_MEMORY,
		NV_VIEWPORT_SWIZZLE,
		NVX_CONDITIONAL_RENDER,
		NVX_GPU_MEMORY_INFO,

		// OVR

		OVR_MULTIVIEW,

		// WGL

		WGL_AMD_GPU_ASSOCIATION,

		WGL_ARB_BUFFER_REGION,
		WGL_ARB_CONTEXT_FLUSH_CONTROL,
		WGL_ARB_CREATE_CONTEXT,
		WGL_ARB_CREATE_CONTEXT_PROFILE,
		WGL_ARB_CREATE_CONTEXT_ROBUSTNESS,
		WGL_ARB_EXTENSIONS_STRING,
		WGL_ARB_FRAMEBUFFER_SRGB,
		WGL_ARB_MAKE_CURRENT_READ,
		WGL_ARB_MULTISAMPLE,
		WGL_ARB_PBUFFER,
		WGL_ARB_PIXEL_FORMAT,
		WGL_ARG_PIXEL_FORMAT_FLOAT,
		WGL_ARB_RENDER_TEXTURE,
		WGL_ARB_ROBUSTNESS_APPLICATION_ISOLATION,

		WGL_ATI_PIXEL_FORMAT_FLOAT,

		// WGL EXT

		WGL_EXT_CREATE_CONTEXT_ES2_PROFILE,
		WGL_EXT_CREATE_CONTEXT_ES_PROFILE,
		WGL_EXT_DEPTH_FLOAT,
		WGL_EXT_EXTENSIONS_STRING,
		WGL_EXT_FRAMEBUFFER_SRGB,
		WGL_EXT_PIXEL_FORMAT_PACKED_FLOAT,
		WGL_EXT_SWAP_CONTROL,

		// WGL NV

		WGL_NV_COPY_IMAGE,
		WGL_NV_DELAY_BEFORE_SWAP,
		WGL_NV_DX_INTEROP,
		WGL_NV_FLOAT_BUFFER,
		WGL_NV_GPU_AFFINITY,
		WGL_NV_MULTISAMPLE_COVERAGE,
		WGL_NV_RENDER_DEPTH_TEXTURE,
		WGL_NV_RENDER_TEXTURE_RECTANGLE,
		WGL_NV_SWAP_GROUP,
		WGL_NV_VERTEX_ARRAY_RANGE


	}

	// initialize OpenGL and GL Features Set
	static {
		GLCapabilities caps = GL.getCapabilities();
		int version = 11;

		VERSIONS.add(11); // OpenGL 1.1 must be supported for LWJGL

		if (caps.OpenGL12) {
			version = 12;
			VERSIONS.add(12);
		}

		if (caps.OpenGL13) {
			version = 13;
			VERSIONS.add(13);
			//GL13 extensions
			FEATURES.add(ARB_TEXTURE_COMPRESSION);
			FEATURES.add(ARB_TEXTURE_CUBE_MAP);
			FEATURES.add(ARB_MULTISAMPLE);
			FEATURES.add(ARB_MULTITEXTURE);
			FEATURES.add(ARB_TEXTURE_ENV_ADD);
			FEATURES.add(ARB_TEXTURE_ENV_COMBINE);
			FEATURES.add(ARB_TEXTURE_ENV_DOT3);
			FEATURES.add(ARB_TEXTURE_BORDER_CLAMP);
			FEATURES.add(ARB_TRANSPOSE_MATRIX);
		}

		if (caps.OpenGL14) {
			version = 14;
			VERSIONS.add(14);
			//GL14 extensions
			FEATURES.add(SGIS_GENERATE_MIPMAP);
			FEATURES.add(NV_BLEND_SQUARE);
			FEATURES.add(ARB_DEPTH_TEXTURE);
			FEATURES.add(ARB_SHADOW);
			FEATURES.add(EXT_FOG_COORD);
			FEATURES.add(EXT_MULTI_DRAW_ARRAYS);
			FEATURES.add(ARB_POINT_PARAMETERS);
			FEATURES.add(EXT_SECONDARY_COLOR);
			FEATURES.add(EXT_BLEND_FUNC_SEPARATE);
			FEATURES.add(EXT_STENCIL_WRAP);
			FEATURES.add(ARB_TEXTURE_ENV_CROSSBAR);
			FEATURES.add(EXT_TEXTURE_LOD_BIAS);
			FEATURES.add(ARB_TEXTURE_MIRRORED_REPEAT);
			FEATURES.add(ARB_WINDOW_POS);
		}

		if (caps.OpenGL15) {
			version = 15;
			VERSIONS.add(15);
			//GL15 extensions
			FEATURES.add(ARB_VERTEX_BUFFER_OBJECT);
			FEATURES.add(ARB_OCCLUSION_QUERY);
			FEATURES.add(EXT_SHADOW_FUNCS);
		}

		if (caps.OpenGL20) {
			version = 20;
			VERSIONS.add(20);
			//GL20 extensions
			FEATURES.add(ARB_SHADER_OBJECTS);
			FEATURES.add(ARB_VERTEX_SHADER);
			FEATURES.add(ARB_FRAGMENT_SHADER);
			FEATURES.add(ARB_SHADING_LANGUAGE_100);
			FEATURES.add(ARB_DRAW_BUFFERS);
			FEATURES.add(ARB_TEXTURE_NON_POWER_OF_TWO);
			FEATURES.add(ARB_POINT_SPRITE);
			FEATURES.add(ATI_SEPARATE_STENCIL);
			FEATURES.add(EXT_STENCIL_TWO_SIDE);
		}

		if (caps.OpenGL21) {
			version = 21;
			VERSIONS.add(21);
			//GL21 extensions
			FEATURES.add(ARB_PIXEL_BUFFER_OBJECT);
			FEATURES.add(EXT_TEXTURE_SRGB);
		}

		if (caps.OpenGL30) {
			version = 30;
			VERSIONS.add(30);
			//GL30 extensions
			FEATURES.add(EXT_GPU_SHADER4);
			FEATURES.add(NV_CONDITIONAL_RENDER);
			FEATURES.add(APPLE_FLUSH_BUFFER_RANGE);
			FEATURES.add(ARB_COLOR_BUFFER_FLOAT);
			FEATURES.add(NV_DEPTH_BUFFER_FLOAT);
			FEATURES.add(ARB_TEXTURE_FLOAT);
			FEATURES.add(EXT_PACKED_FLOAT);
			FEATURES.add(EXT_TEXTURE_SHARED_EXPONENT);
			FEATURES.add(EXT_FRAMEBUFFER_OBJECT);
			FEATURES.add(NV_HALF_FLOAT);
			FEATURES.add(ARB_HALF_FLOAT_PIXEL);
			FEATURES.add(EXT_FRAMEBUFFER_MULTISAMPLE);
			FEATURES.add(EXT_FRAMEBUFFER_BLIT);
			FEATURES.add(EXT_TEXTURE_INTEGER);
			FEATURES.add(EXT_TEXTURE_ARRAY);
			FEATURES.add(EXT_PACKED_DEPTH_STENCIL);
			FEATURES.add(EXT_DRAW_BUFFERS2);
			FEATURES.add(EXT_TEXTURE_COMPRESSION_RGTC);
			FEATURES.add(EXT_TRANSFORM_FEEDBACK);
			FEATURES.add(APPLE_VERTEX_ARRAY_OBJECT);
			FEATURES.add(EXT_FRAMEBUFFER_SRGB);
		}

		if (caps.OpenGL31) {
			version = 31;
			VERSIONS.add(31);
			//GL31 extensions
			FEATURES.add(ARB_DRAW_INSTANCED);
			FEATURES.add(ARB_COPY_BUFFER);
			FEATURES.add(NV_PRIMITIVE_RESTART);
			FEATURES.add(ARB_TEXTURE_BUFFER_OBJECT);
			FEATURES.add(ARB_TEXTURE_RECTANGLE);
			FEATURES.add(ARB_UNIFORM_BUFFER_OBJECT);
		}

		if (caps.OpenGL32) {
			version = 31;
			VERSIONS.add(32);
			//GL32 extensions
			FEATURES.add(ARB_VERTEX_ARRAY_BGRA);
			FEATURES.add(ARB_DRAW_ELEMENTS_BASE_VERTEX);
			FEATURES.add(ARB_FRAGMENT_COORD_CONVENTIONS);
			FEATURES.add(ARB_PROVOKING_VERTEX);
			FEATURES.add(ARB_SEAMLESS_CUBE_MAP);
			FEATURES.add(ARB_TEXTURE_MULTISAMPLE);
			FEATURES.add(ARB_DEPTH_CLAMP);
			FEATURES.add(ARB_GEOMETRY_SHADER4);
			FEATURES.add(ARB_SYNC);
		}

		if (caps.OpenGL33) {
			version = 33;
			VERSIONS.add(33);
			//GL33 extensions
			FEATURES.add(ARB_SHADER_BIT_ENCODING);
			FEATURES.add(ARB_BLEND_FUNC_EXTENDED);
			FEATURES.add(ARB_EXPLICIT_ATTRIB_LOCATION);
			FEATURES.add(ARB_OCCLUSION_QUERY2);
			FEATURES.add(ARB_SAMPLER_OBJECTS);
			FEATURES.add(ARB_TEXTURE_RGB10_A2UI);
			FEATURES.add(ARB_TEXTURE_SWIZZLE);
			FEATURES.add(ARB_TIMER_QUERY);
			FEATURES.add(ARB_INSTANCED_ARRAYS);
			FEATURES.add(ARB_VERTEX_TYPE_2_10_10_10_REV);
		}

		if (caps.OpenGL40) {
			version = 40;
			VERSIONS.add(40);
			//GL40 extensions
			FEATURES.add(ARB_TEXTURE_QUERY_LOD);
			FEATURES.add(ARB_DRAW_BUFFERS_BLEND);
			FEATURES.add(ARB_DRAW_INDIRECT);
			FEATURES.add(ARB_GPU_SHADER5);
			FEATURES.add(ARB_GPU_SHADER_FP64);
			FEATURES.add(ARB_SAMPLE_SHADING);
			FEATURES.add(ARB_SHADER_SUBROUTINE);
			FEATURES.add(ARB_TESSELLATION_SHADER);
			FEATURES.add(ARB_TEXTURE_BUFFER_OBJECT_RGB32);
			FEATURES.add(ARB_TEXTURE_CUBE_MAP_ARRAY);
			FEATURES.add(ARB_TEXTURE_GATHER);
			FEATURES.add(ARB_TRANSFORM_FEEDBACK2);
			FEATURES.add(ARB_TRANSFORM_FEEDBACK3);
		}

		if (caps.OpenGL41) {
			version = 41;
			VERSIONS.add(41);
			//GL41 extensions
			FEATURES.add(ARB_ES2_COMPATIBILITY);
			FEATURES.add(ARB_GET_PROGRAM_BINARY);
			FEATURES.add(ARB_SEPARATE_SHADER_OBJECTS);
			FEATURES.add(ARB_SHADER_PRECISION);
			FEATURES.add(ARB_VERTEX_ATTRIB_64BIT);
			FEATURES.add(ARB_VIEWPORT_ARRAY);

		}

		if (caps.OpenGL42) {
			version = 42;
			VERSIONS.add(42);
			//GL42 extensions
			FEATURES.add(ARB_TEXTURE_COMPRESSION_BPTC);
			FEATURES.add(ARB_COMPRESSED_TEXTURE_PIXEL_STORAGE);
			FEATURES.add(ARB_SHADER_ATOMIC_COUNTERS);
			FEATURES.add(ARB_TEXTURE_STORAGE);
			FEATURES.add(ARB_TRANSFORM_FEEDBACK_INSTANCED);
			FEATURES.add(ARB_BASE_INSTANCE);
			FEATURES.add(ARB_SHADER_IMAGE_LOAD_STORE);
			FEATURES.add(ARB_CONSERVATIVE_DEPTH);
			FEATURES.add(ARB_SHADING_LANGUAGE_420PACK);
			FEATURES.add(ARB_INTERNALFORMAT_QUERY);
			FEATURES.add(ARB_MAP_BUFFER_ALIGNMENT);
		}

		if (caps.OpenGL43) {
			version = 43;
			VERSIONS.add(43);
			//GL43 extensions
			FEATURES.add(ARB_ARRAY_OF_ARRAYS);
			FEATURES.add(ARB_ES3_COMPATIBILITY);
			FEATURES.add(ARB_CLEAR_BUFFER_OBJECT);
			FEATURES.add(ARB_COMPUTE_SHADER);
			FEATURES.add(ARB_COPY_IMAGE);
			FEATURES.add(ARB_DEBUG_GROUP);
			FEATURES.add(ARB_DEBUG_LABEL);
			FEATURES.add(ARB_DEBUG_OUTPUT2);
			FEATURES.add(ARB_DEBUG_OUTPUT);
			FEATURES.add(ARB_EXPLICIT_UNIFORM_LOCATION);
			FEATURES.add(ARB_FRAGMENT_LAYER_VIEWPORT);
			FEATURES.add(ARB_FRAMEBUFFER_NO_ATTACHMENTS);
			FEATURES.add(ARB_INTERNALFORMAT_QUERY2);
			FEATURES.add(ARB_INVALIDATE_SUBDATA);
			FEATURES.add(ARB_MULTI_DRAW_INDIRECT);
			FEATURES.add(ARB_PROGRAM_INTERFACE_QUERY);
			FEATURES.add(ARB_ROBUST_BUFFER_ACCESS_BEHAVIOR);
			FEATURES.add(ARB_SHADER_IMAGE_SIZE);
			FEATURES.add(ARB_SHADER_STORAGE_BUFFER_OBJECT);
			FEATURES.add(ARB_STENCIL_TEXTURING);
			FEATURES.add(ARB_TEXTURE_BUFFER_RANGE);
			FEATURES.add(ARB_TEXTURE_QUERY_LEVELS);
			FEATURES.add(ARB_TEXTURE_STORAGE_MULTISAMPLE);
			FEATURES.add(ARB_TEXTURE_VIEW);
			FEATURES.add(ARB_VERTEX_ATTRIB_BINDING);
		}

		if (caps.OpenGL44) {
			version = 44;
			VERSIONS.add(44);
			//GL44 extensions
			FEATURES.add(ARB_BUFFER_STORAGE);
			FEATURES.add(ARB_CLEAR_TEXTURE);
			FEATURES.add(ARB_ENHANCED_LAYOUTS);
			FEATURES.add(ARB_MULTI_BIND);
			FEATURES.add(ARB_QUERY_BUFFER_OBJECT);
			FEATURES.add(ARB_TEXTURE_MIRROR_CLAMP_TO_EDGE);
			FEATURES.add(ARB_TEXTURE_STENCIL8);
			FEATURES.add(ARB_VERTEX_TYPE_10F_11F_11F_REV);
		}

		if (caps.OpenGL45) {
			version = 45;
			VERSIONS.add(45);
			//GL45 extensions
			FEATURES.add(ARB_CLIP_CONTROL);
			FEATURES.add(ARB_CULL_DISTANCE);
			FEATURES.add(ARB_ES31_COMPATIBILITY);
			FEATURES.add(ARB_CONDITIONAL_RENDER_INVERTED);
			FEATURES.add(KHR_CONTEXT_FLUSH_CONTROL);
			FEATURES.add(ARB_DERIVATIVE_CONTROL);
			FEATURES.add(ARB_DIRECT_STATE_ACCESS);
			FEATURES.add(ARB_GET_TEXTURE_SUB_IMAGE);
			FEATURES.add(KHR_ROBUSTNESS);
			FEATURES.add(ARB_SHADER_TEXTURE_IMAGE_SAMPLES);
			FEATURES.add(ARB_TEXTURE_BARRIER);
		}
		GL.createCapabilities();
		VERSION = version;
	}

	/**
	 * Checks if the specified feature is supported by this platform.
	 * <p>
	 * <em>Note: this method checks for platform support of a feature
	 * regardless
	 * of OpenGL context.</em> To check if the feature is available from the
	 * current profile, use {@link GLProfile#supports(Feature)}.
	 * </p>
	 *
	 * @param feature
	 * 		the feature to check for
	 *
	 * @return true if the feature is supported
	 */
	public static boolean supports(Feature feature) {
		return FEATURES.contains(feature);
	}

	/**
	 * Checks whether the specified version is supported
	 *
	 * @param version
	 * 		the version to check for
	 *
	 * @return true if the version is supported
	 */
	public static boolean supports(int version) {
		return VERSIONS.contains(version);
	}

	/**
	 * Gets the latest version of OpenGL that this platform supports
	 *
	 * @return the latest version of OpenGL that this platform supports
	 */
	public static int getVersion() {
		return VERSION;
	}


	/**
	 * depending on the calling method and what methods will be used in opengl
	 * specifically, this will initialize the correct Gl features.
	 */
	@CallerSensitive
	public static void initialize() {
		GL.createCapabilities();
	}


	public static GLProfile getProfile() {
		synchronized (OpenGL.class) {
			return profile;
		}
	}

	public static GLContext getContext() {
		return context;
	}

	public static void setContext(GLContext context) {
		synchronized (OpenGL.class) {
			OpenGL.profile = context.getProfile();
			OpenGL.context = context;
		}
	}

	public static GLImage image(int target, int lod, int type, int width,
	                            int height, int borderWidth, int pixelFormat,
	                            Color[] pixels) {
		return profile.image(target, lod, type, width, height, borderWidth, pixelFormat, pixels);
	}

	public static GLBuffer buffer(int target, int size, int usage) {
		return profile.buffer(target, size, usage);
	}

}
