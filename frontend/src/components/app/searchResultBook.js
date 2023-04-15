import React from 'react';
import Service from "../../repository/Repository";
import {useLocation} from "react-router-dom";

const SearchResultBook = ({ data }) => {
    const location=useLocation()
    const mydata=location.state
    const onFormSubmit =async (e) => {
        e.preventDefault();
        console.log(mydata)
    }
    return (
        <div style={{ backgroundColor: '#f2f2f2', minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div style={{ maxWidth: '800px', margin: '0 auto', padding: '40px', backgroundColor: '#fff', borderRadius: '10px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)', textAlign: 'center' }}>
                {mydata.label && <h1 style={{ fontSize: '56px', fontWeight: 'bold', marginBottom: '40px', color: '#212121', textTransform: 'uppercase' }}>{mydata.label}</h1>}
                {mydata.releaseDate && mydata.country && mydata.language && (
                    <p style={{ fontSize: '24px', lineHeight: '1.5', color: '#666', marginTop: '0', marginBottom: '40px', fontStyle: 'italic' }}>
                        Published in <span style={{ color: '#0f9d58', fontWeight: 'bold' }}>{mydata.releaseDate}</span> in <span style={{ color: '#0f9d58', fontWeight: 'bold' }}>{mydata.country}</span> and written in <span style={{ color: '#0f9d58', fontWeight: 'bold' }}>{mydata.language}</span>
                    </p>
                )}
                {mydata.abstract && <p style={{ fontSize: '24px', lineHeight: '1.5', color: '#212121', marginTop: '0', marginBottom: '0' }}>{mydata.abstract}</p>}
            </div>
        </div>
    );
};

export default SearchResultBook;