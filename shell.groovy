def executeCommand(String command) {
    println "Running command $command"

    def result = command.execute()

    println result.text
}

def getEnvironmentVaribale() {
    def currentEnv = System.getenv()
    def result = new HashMap()
    def pluginPrefix = 'PLUGIN_'

    currentEnv.each {key, value ->
        if (key.startsWith(pluginPrefix)) {
            result[key.replaceFirst(pluginPrefix, "")] = value
        }
    }

    return result
}
