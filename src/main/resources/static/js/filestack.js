
// Make sure to put your personal FileStackKey in the keys.js file
const client = filestack.init(FileStackApiKey);

// we will pass this object as an argument in the picker method.
const options = {

    //onFileUploadFinished is called when the the user uploads a image in the
    // picker and they have successfully uploaded the image to filestack servers.
    //
    onFileUploadFinished: callback =>{

        // I save the filestack image url to a const because I plan to use it in multiple places.
        const imgURL = callback.url;

        // this sets my hidden input to the value of my new image url.
        $('#image').val(imgURL);

        // this lets the user see a preview of the image that they uploaded.
        $('#imagePreview').attr('src',imgURL);
    }
}
// This is an event listen for listening to a click on a button
$('#addPicture').click(function (event){

    // this is what prevents the button from submiting the form
    event.preventDefault();

    //we use this to tell filestack to open their file picker interface.
    // the picker method can take an argument of a options object
    // where you can specify what you want the picker to do
    client.picker(options).open();
})


