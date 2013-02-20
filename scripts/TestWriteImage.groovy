import privatewall.ImportImages

ImportImages importImages = new ImportImages()
importImages.ImportFromGmail(System.getProperty("email"), System.getProperty("password"))
