package privatewall

class Content {

    byte[] fileContent
    String fileName
    Date dateCreated

    static belongsTo = [ user : User /* , post : Post */ ]

    static constraints = {
        fileContent blank: false
        fileName blank: false
    }
}
