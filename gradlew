#!/usr/bin/env sh
# Gradle wrapper script (minimal, standard form)
# Generated for Unix-like environments to match existing gradle-wrapper.properties
set -e

APP_NAME="gradlew"
APP_LONG_NAME="Gradle Wrapper"

DEFAULT_JVM_OPTS=""

if [ -z "$JAVA_HOME" ]; then
  JAVACMD=`which java 2>/dev/null || true`
else
  JAVACMD="$JAVA_HOME/bin/java"
fi

if [ -z "$JAVACMD" ]; then
  JAVACMD=java
fi

CLASSPATH="$0" # not used but kept for compatibility

DIRNAME=`dirname "$0"`
PROGNAME=`basename "$0"`

exec "$JAVACMD" $DEFAULT_JVM_OPTS -classpath "$DIRNAME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
