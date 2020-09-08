export default class VideoServices {

    getAllVideos() {
        const request = new XMLHttpRequest();
        return new Promise(function (resolve, reject) {
            request.onload = function() {
                if (request.status >= 200 && request.status < 300) {
                    const threads = JSON.parse(request.responseText);
                    resolve(threads);
                } else {
                    reject({
                        status: request.status,
                        statusText: request.statusText
                    });
                }
            };
            request.open("GET", "/api/videos/home");
            request.send();
        })
    }
}
