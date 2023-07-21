import React from "react";
import {API_BASE_URL} from "../api-config";

export function call(api, method, request) {
    const headers = new Headers({
        "Content-Type": "application/json"
    });

    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    if (accessToken) {
        headers.append("Authorization", accessToken);
    }

    let options = {
        headers: headers,
        url: API_BASE_URL + api,
        method: method
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options).then((response) => {
        if (response.status === 403) {
            window.location.href = "/";
            return;
        }
        return response.json();
    }).catch((error) => {
        console.log("http error");
        console.log(error);
    });
}

export function socialLogin(provider) {
    window.location.href = API_BASE_URL + "/auth/authorize/" + provider;
}
