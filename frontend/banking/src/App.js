import './App.css';
import 'bootstrap/dist/css/bootstrap.css'
import React from 'react'
import {socialLogin} from "./service/ApiService";

function App() {
    console.log(localStorage.getItem("member"))
    const handleSocialLogin = (provider) => {
        socialLogin(provider);
    };

    return (
        <div>
            <div className="container" style={{maxWidth: 600}}>
                <div className="py-5 text-center">
                    <p style={{fontSize: 100, color: "blue"}}>Togg</p>
                    <p style={{fontSize: 25}}>토스 서술형에서 언급된 트랜잭션 단위와 Lock 개념을 실제 적용하고 학습해보는 계좌 이체 서비스</p>
                    <p>{localStorage.getItem("member")}</p>
                </div>

                <div className="row">
                    <div className="col">
                        <button className="w-100 btn btn-secondary btn-lg"
                                type="button" onClick={() => handleSocialLogin("google")}>
                            구글 로그인
                        </button>
                    </div>
                    <div className="col">
                        <button className="w-100 btn btn-secondary btn-lg"
                                type="button" onClick={() => handleSocialLogin("naver")}>
                            네이버 로그인
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
