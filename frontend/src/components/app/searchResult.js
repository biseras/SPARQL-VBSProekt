import React from 'react';

const SearchResult = (props) => {
    const { data } = props.location.state;
    return (
        <div>
            <h1>{data.searchname}</h1>
            <p>Birthplace: {data.birthPlace}</p>
            <img src={data.thumbnail} alt={data.searchname} />
        </div>
    )
}

export default SearchResult;