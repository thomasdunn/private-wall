package privatewall

class User {

    String userId
    String password
    Date dateCreated

    static hasMany = [ posts : Post ]

    static constraints = {
        userId size: 3..20, unique: true
        password size: 6..20
    }
}
