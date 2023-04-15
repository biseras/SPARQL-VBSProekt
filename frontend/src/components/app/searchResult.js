import React from 'react';
import Service from "../../repository/Repository";
import {useLocation} from "react-router-dom";

const SearchResult = ({ data }) => {
    const location=useLocation()
    const mydata=location.state
    const onFormSubmit =async (e) => {
        e.preventDefault();
        console.log(mydata)
    }
    return (
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", backgroundColor: "#f7f7f7", padding: "20px" }}>
            <h1 style={{ fontSize: "32px", margin: "20px 0", color: "#333" }}>{mydata.searchname}</h1>
            <img src={mydata.thumbnail} alt={mydata.searchname} style={{ width: "50%", marginBottom: "20px", borderRadius: "10px", boxShadow: "0px 0px 10px #ccc" }} />
            <p style={{ fontSize: "18px", textAlign: "center", maxWidth: "600px", color: "#666" }}>{mydata.abstract}</p>
            <h2 style={{ fontSize: "24px", margin: "20px 0", color: "#333" }}>Books:</h2>
            <ul style={{ listStyle: "none", paddingLeft: 0 }}>
                {mydata.book.map((book) => (
                    <li key={book} style={{ fontSize: "18px", marginBottom: "10px", color: "#666" }}>{book}</li>
                ))}
            </ul>
        </div>
    );
};

export default SearchResult;