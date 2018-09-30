java_library(
    name = "assertj3_11_1",
    visibility = ["//visibility:public"],
    exports = ["@assertj3_11_1//jar"],
)

java_library(
    name = "junit4_12",
    visibility = ["//visibility:public"],
    exports = ["@junit4_12//jar"],
)

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library", "kt_jvm_binary", "kt_jvm_test")

kt_jvm_binary(
    name = "part1",
    srcs = glob([
        "Part1.kt",
    ]),
    main_class = "y2015.Part1Kt",
    data = ["input"],
)

kt_jvm_binary(
    name = "part2",
    srcs = glob([
        "Part2.kt",
    ]),
    main_class = "y2015.Part2Kt",
    data = ["input"],
)

kt_jvm_test(
    name = "tests",
    srcs = glob(["*.kt"]),
    test_class = "y2015.Tests",
    deps = ["assertj3_11_1", "junit4_12", "@com_github_jetbrains_kotlin//:kotlin-test"],
    data = ["input"],
)