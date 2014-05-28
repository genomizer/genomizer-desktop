#Genomizer Desktop

Welcome to the Genomizer swing client!
Programvaruteknik, Umea University Spring 2014

**Bugs and problems are to be reported to the dev team as soon as possible, either by GitHub Issues or by mail to genomizer@mailinator.com**
*See User Manual for instructions on using functionality*

##CHANGELOG V1.0.0 RELEASE DATE 14-05-28

- Nicer login screen
- Added the genomizer logo and some nice icons
- Switch between Query builder and Manual edit in search criterias
- Choose Genome Release version when uploading profile and region files
- Please try out the process functionality, preferrably with valid files and 'good' values.
- View Process info from the server
- Shorter workflow when downloading a file, with a tab to show progress
- Option to upload files to an experiment from Workspace
- Administation of annotation *should* work.
- Add genome release files in administration is working, but lacks visual appeal and user feedback.
 

###NOTES
- Please see Help box and User Manual for guides on using the process
- File download does not control preventing of writing over an existing file if they have the same file name.
- You can now also press enter to login and to search(?)
- When adding really small genome release files not much happens.
- It is possible to break add genome release uploads in many colorful ways by suppling *wrong* files and with bad names (not A-Z,0-9, non ascii etc)

##CHANGELOG v0.2.0 RELEASE DATE 14-05-20

- Batch upload experiments
- Delete experiments and files

###NOTES
- Process has still not been ready for system testing
- Drag & Drop is stable
- Search Results updates itself
- Minor design improvements
- ~~Annotations are case sensitive~~ fixed.
- ~~Can not upload files with spaces in the name~~ fixed.
- ~~Can not upload files that are compressed~~ fixed.
- 

##CHANGELOG v0.1.1 RELEASE DATE 14-05-16

- Bugs reported by Yuri on 14-05-13 fixed.
- Changed Look & Feel to be more persistent over all platforms.
- Search results can be sorted by columns and user can choose which annotations to view.
- Upload to new Experiment.
- Upload files to existing Experiment.
- Drag and drop files to upload tab and they are added.
- Progress bars for uploading and downloading files.
- Sending request for converting RawToProfile on Process, but all user choices are not implemented yet, and genome file chooser is not functional. 
- Add and see active annotations under the Administration Tab.

###NOTES

- Process is unfortunately still not working all the way, no file will be generated
- Some problems occur on files with same name
- Uploading files to existing experiment is sensitive to experiment name, and must match precisely.
- Can not upload files with spaces in the name.
- Can not upload files that are compressed.
- If errors occur during upload, the file is added to the database but is 'incomplete', this error is not catched as of now.
- Annotations are case sensitive

####About the source
The code has been formatted by eclipse and IntelliJ's source - format. An Eclipse code style format can be imported from the file /resources/Eclipse[genomizer].xml. The code style uses only whitespaces instead of tabs and curly brackets on the same line.
