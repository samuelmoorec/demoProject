
const client = filestack.init(FileStackApiKey);

const options = {
    onUploadDone: r =>{
    const newImageUrl = r.filesUploaded[0].url;
    $('#image').val(newImageUrl);
    $('#imagePreview').attr('src',newImageUrl);
    }
}

$('#addPicture').click(function(e){
    e.preventDefault();
    client.picker(options).open();

})


