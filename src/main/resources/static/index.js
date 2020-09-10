import VideoServices from "./video-service.js";

const videoServices = new VideoServices();

window.addEventListener("load", function () {
    document.getElementById("greeting").innerHTML = `Welcome to Channel Ca$hMoney!`;
    videoServices
        .getAllVideos()
        .then(successCallback, errorCallback);

   function successCallback(response) {
       populateVideos(response);
   }

   function errorCallback(response) {
       console.log(response);
   };
});

function populateVideos(videos) {
    videos.forEach(video => {
        addVideoToThread(video);
    })
}

function addVideoToThread(video) {
    const videoHeading = document.createElement("h3");
    const titleContent = document.createTextNode(video.title);
    videoHeading.appendChild(titleContent);

    const videoParagraph = document.createElement("p");
    const authorContent = document.createTextNode(video.author);
    videoParagraph.appendChild(authorContent);

    const videoListItem = document.createElement("LI");
    videoListItem.appendChild(videoHeading).appendChild(videoParagraph);
    document.getElementById("video-list").appendChild(videoListItem);
}