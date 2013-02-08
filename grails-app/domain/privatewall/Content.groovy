package privatewall

class Content {

    byte[] fileContent
    String fileName
    Date dateCreated

    static belongsTo = [ user : User /* , post : Post */ ]

    static constraints = {
        fileContent blank: false, minSize:1, maxSize: 10 * 1024 * 1024
        fileName blank: false
    }

    static mapping = {
        columns {
            fileContent sqlType: 'blob'
        }
    }
}
