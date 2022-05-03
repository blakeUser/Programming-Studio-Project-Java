get_filename_component(CINDER_PATH "${CMAKE_CURRENT_SOURCE_DIR}/../../../" ABSOLUTE)

file(GLOB SOURCE_LIST CONFIGURE_DEPENDS
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/apps/*.h"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/apps/*.hpp"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/apps/*.cc"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/apps/*.cpp"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/*.h"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/*.hpp"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/*.cc"
        "/Users/song15101556787/Desktop/cinder_0.9.2_mac/my-projects/untitled/*.cpp"
        )

include("/Users/song15101556787/Desktop/cinder_0.9.2_mac/proj/cmake/modules/cinderMakeApp.cmake")

ci_make_app(
        APP_NAME    cinder-snake
        CINDER_PATH ${CINDER_PATH}
        SOURCES     ${SOURCE_LIST}
)

target_compile_features(cinder-snake PRIVATE cxx_std_14)

# Cross-platform compiler lints
if (${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang"
        OR ${CMAKE_CXX_COMPILER_ID} STREQUAL "GNU")
    target_compile_options(cinder-snake PRIVATE
            -Wall
            -Wextra
            -Wswitch
            -Wconversion
            -Wparentheses
            -Wfloat-equal
            -Wzero-as-null-pointer-constant
            -Wpedantic
            -pedantic
            -pedantic-errors)
elseif (${CMAKE_CXX_COMPILER_ID} STREQUAL "MSVC")
    cmake_policy(SET CMP0015 NEW)
    target_compile_options(cinder-snake PRIVATE
            /W3)
endif ()
