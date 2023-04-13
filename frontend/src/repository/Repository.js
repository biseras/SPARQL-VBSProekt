import axios from "../costum-axios/axios";
const Service={
    searchAuthor: (name) => {
        return axios.post("/searchauthor", {
            "name":name
        }).then(response => {
            return response.data;
        })
    },
    searchBook: (name) => {
        return axios.post("/searchbook", {
            "name":name
        }).then(response => {
            return response.data;
        })
    }
}

export default Service;