Rate limiter Exercise

How to run:

    linux:
        $: base_proyect_dir/gradlew bootrun
    
    windows(check):
        >: base_proyect_dir/gradle.bat bootRun

Service endpoints:

    http://localhost:8080/email-sender

Body example:

    {
    "receiverUserId":"user1",
    "message":"Message2",
    "emailType":"NEWS"
    }


Availables emails types: 'STATUS', 'NEWS', 'MARKETING'


Acces to H2 admin console

    http://localhost:8080/h2-console
    user and passwd located in properties file

Bucket4j Documentation

    https://bucket4j.com/8.4.0/toc.html



Facundo Curotto

facundocurotto@gmail.com