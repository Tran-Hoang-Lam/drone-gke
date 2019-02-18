static Script getShellScript() {
    GroovyShell shell = new GroovyShell()
    return shell.parse(new File('/var/drone-gke/shell.groovy'))
}

def authorizeToGke(String serviceAccountFile, String email, String projectId) {
    println "Start authorize to GKE"
    def authorizeCommand = "gcloud auth activate-service-account $email --key-file $serviceAccountFile --project $projectId"

    getShellScript().executeCommand(authorizeCommand)
}

def setCluster(String clusterName, String zone) {
    println "Starting set cluster"
    def setClusterCommand = "gcloud container clusters get-credentials $clusterName --zone $zone"

    getShellScript().executeCommand(setClusterCommand)
}

def processTemplate(String path, Map env) {
    println "Start processing template $path"

    def template = new File(path).text

    env.each {key, value ->
        template = template.replaceAll($/\$\{$key\}/$, value)
    }

    return template
}

def applyTemplate(String path) {
    println "Starting apply template"

    def applyCommand = "kubectl apply -f $path"

    getShellScript().executeCommand(applyCommand)
}

def generateDockerConfigAndSaveToEnv(String serviceAccount, HashMap environmentParam) {
    println "Generate docker config"

    def serviceAccountJson = new String(serviceAccount.decodeBase64())
    def registryHost = environmentParam.REGISTRY_HOST ?: 'asia.gcr.io'

    def auth = "_json_key:$serviceAccountJson".bytes.encodeBase64().toString()

    def auths = """
        {
            "auths": {
              "$registryHost": {
                 "auth": "$auth"
              }
            }
        }
    """.trim().stripIndent().bytes.encodeBase64().toString()

    environmentParam.DOCKER_CONFIG = auths
}
