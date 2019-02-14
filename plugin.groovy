Script getShellScript() {
    GroovyShell shell = new GroovyShell()
    return shell.parse(new File('shell.groovy'))
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
