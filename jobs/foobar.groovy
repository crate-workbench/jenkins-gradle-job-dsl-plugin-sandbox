job('foobar') {
    description "Test job"
    triggers {
    }
    logRotator {
        daysToKeep 5
    }
    steps {
        shell('make test')
    }
}
