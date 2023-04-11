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
        <div>
            <h1>{mydata.birthname}</h1>
        </div>
    );
};

export default SearchResult;