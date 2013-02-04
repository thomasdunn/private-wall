package privatewall

class Post {

    String body
    Date dateCreated

    static belongsTo = [ user : User ]
    static hasMany = [ contents : Content ]

    static constraints = {
        body blank: false
    }
}
