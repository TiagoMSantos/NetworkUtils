setup:
		./gradlew androidDependencies

clean-cache:
		./gradlew cleanBuildCache

clean-gradle:
		./gradlew clean

clean:	clean-cache clean-gradle

ktlintFormat:
		./gradlew ktlintFormat

detekt:
		./gradlew detekt

lint:	ktlint detekt

code-format:
		./gradlew ktlintFormat

build-debug:
		./gradlew assembleDebug

build-release:
		./gradlew assembleRelease

build:	build-debug build-release

test-debug:
		./gradlew testDebugUnitTest

check:	clean ktlintFormat test-debug
