import React, { useState } from "react";
import {call} from "../service/ApiService";

function SignUp() {
    const addType = (type) => {
        call("/signup", "POST", type);
        window.location.href = "/account";
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const type = data.get("type")
        addType({
            investmentType : type
        });
    }

    return (
        <div className="container" style={{maxWidth: 600}}>
            <div className="py-5 text-center">
                <h2>회원가입</h2>
            </div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="type">투자 타입</label>
                    <input name="type" className="form-control" placeholder="투자 타입을 입력하세요"/>
                </div>

                <hr className="my-4"/>

                <div className="row">
                    <div className="col">
                        <button className="w-100 btn btn-primary btn-lg" type="submit">가입</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default SignUp;
