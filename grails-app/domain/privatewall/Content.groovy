package privatewall

class Content {

    byte[] fileContent
    String fileName
    Date dateCreated

    static belongsTo = [ user : User /* , post : Post */ ]

    static constraints = {
        fileContent blank: false, minSize:1, maxSize: Integer.MAX_VALUE
        fileName blank: false
    }

    static mapping = {
        columns {
            fileContent sqlType: 'blob'
        }
    }
}
