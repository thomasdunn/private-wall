import privatewall.User

System.getProperty("userIds").split(/ /).each {
    createUser(it)
}

def createUser(String userId) {
    if (User.findByUserId(userId) == null) {
        def user = new User(userId: userId, password: "123456")
        user.save()
        println user.getErrors()
        println "$userId saved."
    }
    else {
        println "$userId already exists."
    }
}
