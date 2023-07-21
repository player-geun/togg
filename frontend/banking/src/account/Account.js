import React, {useEffect, useState} from "react";
import {call} from "../service/ApiService";
import { useNavigate } from "react-router-dom";

function Account() {
    const [member, setMember] = useState({});
    const [accounts, setAccounts] = useState([]);

    useEffect(() => {
        call("/api/members/me", "GET")
            .then((response) => {
                setMember(response);
            });
    }, []);

    useEffect(() => {
        call("/api/accounts/me", "GET")
            .then((response) => {
                setAccounts(response.accounts);
            });
    }, []);

    const navigate = useNavigate();

    const onClickAccount = (account) => {
        navigate("/account/transfer", { state : { number: account.number, balance: account.balance } })
    }

    const myAccount = (account) => {
        return (
            <tbody key={account.id}>
            <tr key={account.id}>
                <td><a onClick={() => onClickAccount(account)}>{account.id}</a></td>
                <td><a onClick={() => onClickAccount(account)}>{account.number}</a></td>
                <td><a onClick={() => onClickAccount(account)}>{account.balance}</a></td>

            </tr>
            </tbody>
        );
    }

    const myAccounts = accounts && accounts.map((account) => myAccount(account))

    const onClickButton = () => {
        call("/api/accounts", "POST")
            .then((response => setAccounts([...accounts, response])));
    }

    return (
        <div className="container" style={{maxWidth: 600}}>
            <div className="py-5 text-center">
                <h2>{member.name} 님의 계좌</h2>
            </div>
            <div className="row">
                <div className="col">
                    <button className="btn btn-primary float-end" onClick={onClickButton}
                            type="button">계좌 등록
                    </button>
                </div>
            </div>

            <hr className="my-4"/>

            <div>
                <table className="table">
                    <thead>
                    <tr>
                        <th>계좌 ID</th>
                        <th>계좌 번호</th>
                        <th>잔액</th>
                    </tr>
                    </thead>
                        {myAccounts}
                </table>
            </div>
        </div>
    );
}

export default Account;
