#!/usr/bin/env bash
set -e

VERSION=`grep -nri "<tag>" pom.xml | grep -oE '(\d+\.\d+\.\d+)'`
echo VERSION $VERSION

rm -f runelite-client/target/*.jar
mvn package -DskipTests -U
cp runelite-client/target/client-*-shaded.jar ~/Desktop/runelite.jar
cp runelite-client/target/client-*-shaded.jar ~/Dropbox/RuneLite/RuneLite-while-loop-$VERSION.jar

