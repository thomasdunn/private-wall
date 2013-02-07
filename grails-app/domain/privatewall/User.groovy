package privatewall

class User {

    String userId
    String password
    Date dateCreated

    static hasMany = [ contents : Content /* , posts : Post */ ]

    static constraints = {
        userId size: 3..50, unique: true
        password size: 6..20
    }

    String toString() {
        "User ${userId} (${id})"
    }
}
