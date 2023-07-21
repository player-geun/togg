import React from "react";
import { Navigate } from "react-router-dom";

const SocialLogin = (props) => {
    const getUrlParameter = (name) => {
        let search = window.location.search;
        let params = new URLSearchParams(search);
        return params.get(name);
    };

    const token = getUrlParameter('token');

    if (token) {
        localStorage.setItem("ACCESS_TOKEN", token);

        return (
            <Navigate
                to={{
                    pathname: "/signup",
                    state: {from: props.location}
                }}
            />
        );
    }

    return (
        <Navigate
            to={{
                pathname: "/",
                state: {from: props.location}
            }}
        />
    )
}

export default SocialLogin;
