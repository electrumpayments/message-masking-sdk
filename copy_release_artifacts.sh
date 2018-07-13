# collects release artifacts and copies them to the build Directory

buildName=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.artifactId}' --non-recursive exec:exec)
buildVersion=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)

# check all is in order
if [ ! -e target/${buildName}-${buildVersion}-javadoc.jar ]
then
  echo "File target/${buildName}-${buildVersion}-javadoc.jar not found: exiting"
  exit 1
fi
if [ ! -e target/${buildName}-${buildVersion}-sources.jar ]
then
  echo "File target/${buildName}-${buildVersion}-sources.jar not found: exiting"
  exit 1
fi
if [ ! -e target/${buildName}-${buildVersion}.jar ]
then
  echo "File target/${buildName}-${buildVersion}.jar not found: exiting"
  exit 1
fi

# make the builds directory pristine again
if [ ! -d builds ]
then
  echo "Creating builds folder"
  mkdir builds
fi

echo "Removing old files"
rm -rf builds/*

# copy artifacts
echo "Copying artifacts"
cp target/${buildName}-${buildVersion}-javadoc.jar builds
cp target/${buildName}-${buildVersion}-sources.jar builds
cp target/${buildName}-${buildVersion}.jar builds
