node {
    checkout scm
    def rootDir = pwd()
    println("Current Directory: " + rootDir)
    def dockerbuild = load "${rootDir}/first-pipeline.groovy"
}