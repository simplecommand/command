if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
    exit 1
fi

echo Release version $1

cp ../target/command-$1.jar .
cp ../target/command-$1-javadoc.jar .
cp ../source.directory/simple-command-sources.jar .
mv simple-command-sources.jar command-$1-sources.jar
cp ../pom.xml .
rm -rf bundle.jar

gpg -ab pom.xml
gpg -ab command-$1.jar
gpg -ab command-$1-javadoc.jar
gpg -ab command-$1-sources.jar

jar -cfv bundle.jar command-$1.jar command-$1.jar.asc command-$1-javadoc.jar command-$1-javadoc.jar.asc command-$1-sources.jar command-$1-sources.jar.asc pom.xml pom.xml.asc 

