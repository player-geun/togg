import React, {useEffect, useState} from "react";
import {call} from "../service/ApiService";
import {useLocation} from "react-router-dom";

function AccountTransfer () {
    const [givenAccountTransfers, setGivenAccountTransfers] = useState([]);
    const [receivedAccountTransfers, setReceivedAccountTransfers] = useState([]);

    const accountNumber = useLocation().state.number;

    useEffect(() => {
        call("/api/accounts/" + accountNumber + "/transfers", "GET")
            .then((response) => {
                setGivenAccountTransfers(response.givenAccountTransfers);
                setReceivedAccountTransfers(response.receivedAccountTransfers);
            });
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const number = data.get("number");
        const amount = data.get("amount");
        call("/api/accounts/transfers", "POST", {
            giverAccountNumber: accountNumber,
            receiverAccountNumber: number,
            amount: amount
        })
            .then((response) => {
                setGivenAccountTransfers([...givenAccountTransfers, response]);
            });
    }

    const myAccountTransfer = (accountTransfer) => {
        return (
            <tbody key={accountTransfer.id}>
                <tr key={accountTransfer.id}>
                    <td><a>{accountTransfer.giverAccountNumber}</a></td>
                    <td><a>{accountTransfer.receiverAccountNumber}</a></td>
                    <td><a>{accountTransfer.amount}</a></td>
                </tr>
            </tbody>
        );
    }

    const myAccountTransfers = (accountTransfers) =>
        accountTransfers && accountTransfers.map((accountTransfer) => myAccountTransfer(accountTransfer));

    return (
        <div className="container" style={{maxWidth: 600}}>
            <div className="py-5 text-center">
                <h2>계좌이체 내역</h2>
            </div>

            <p>나의 계좌 번호 : {accountNumber}</p>

            <hr className="my-4"/>

            <div className="col">
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="number">입금할 계좌번호</label>
                        <input name="number" className="form-control" placeholder="입금할 금액을 입력하세요"/>
                    </div>
                    <div>
                        <label htmlFor="amount">금액</label>
                        <input name="amount" className="form-control" placeholder="입금할 금액을 입력하세요"/>
                    </div>

                    <hr className="my-4"/>

                    <div className="row">
                        <div className="col">
                            <button className="w-100 btn btn-primary btn-lg" type="submit">계좌이체</button>
                        </div>
                    </div>
                </form>
            </div>

            <hr className="my-4"/>

            <div>
                <h4>입금 내역</h4>
                <table className="table">
                    <thead>
                    <tr>
                        <th>상대방의 계좌 번호</th>
                        <th>나의 계좌 번호</th>
                        <th>금액</th>
                    </tr>
                    </thead>
                    {myAccountTransfers(receivedAccountTransfers)}
                </table>

                <hr className="my-4"/>

                <h4>출금 내역</h4>
                <table className="table">
                    <thead>
                    <tr>
                        <th>나의 계좌 번호</th>
                        <th>상대방의 계좌 번호</th>
                        <th>금액</th>
                    </tr>
                    </thead>
                    {myAccountTransfers(givenAccountTransfers)}
                </table>
            </div>
        </div>
    );
}

export default AccountTransfer;
