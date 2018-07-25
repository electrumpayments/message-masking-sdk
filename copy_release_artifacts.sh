#!/bin/bash

# collects release artifacts and copies them to the build Directory
projectFolder="."
buildName=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.artifactId}' --non-recursive exec:exec)
buildVersion=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)

# check all is in order
if [ ! -e ${projectFolder}/target/${buildName}-${buildVersion}-javadoc.jar ]
then
  echo "File ${projectFolder}/target/${buildName}-${buildVersion}-javadoc.jar not found: exiting"
  exit 1
fi
if [ ! -e ${projectFolder}/target/${buildName}-${buildVersion}-sources.jar ]
then
  echo "File ${projectFolder}/target/${buildName}-${buildVersion}-sources.jar not found: exiting"
  exit 1
fi
if [ ! -e ${projectFolder}/target/${buildName}-${buildVersion}.jar ]
then
  echo "File ${projectFolder}/target/${buildName}-${buildVersion}.jar not found: exiting"
  exit 1
fi

# make the builds directory pristine again
if [ ! -d ${projectFolder}/builds ]
then
  echo "Creating builds folder"
  mkdir ${projectFolder}/builds
fi

echo "Removing old files"
rm -rf ${projectFolder}/builds/*

# copy artifacts
echo "Copying artifacts"
cp ${projectFolder}/target/${buildName}-${buildVersion}-javadoc.jar builds
cp ${projectFolder}/target/${buildName}-${buildVersion}-sources.jar builds
cp ${projectFolder}/target/${buildName}-${buildVersion}.jar builds
