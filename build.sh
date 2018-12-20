#!/usr/bin/env bash
set -e
# git fetch --all --tags
# git rebase tags/runelite-parent-1.5.5
VERSION=`grep -nri "<tag>" pom.xml | grep -oE '(\d+\.\d+\.\d+)'`
echo VERSION $VERSION

rm -f runelite-client/target/*.jar
mvn package -DskipTests -U
cp runelite-client/target/client-*-shaded.jar ~/Desktop/runelite.jar
cp runelite-client/target/client-*-shaded.jar ~/Dropbox/RuneLite/RuneLite-while-loop-$VERSION.jar

