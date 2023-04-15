import React from "react";
import SearchAuthor from "./searchAuthor";
import Showromanticismbooks from "./showromanticismbooks";
import SearchBook from "./searchBook";
import {Component} from "react";
import Header from "./Header";
import {
  Routes,
  Navigate,
  Route, Router
} from 'react-router-dom';
import SearchResult from './searchResult';
import SearchResultBook from './searchResultBook';
import Service from "../../repository/Repository";

class App extends Component{
    constructor(props) {
        super(props);
        this.state={
            author:[],
            book:[],
            booksrom:[]
        }
    }
  render() {
    return (
        <div>
          <Header/>
          <div className={"container"}>
            <Routes>
                <Route path="/searchauthor" element={<SearchAuthor onsearch={this.searchAuthor} />} />
                <Route path="/result" element={<SearchResult/>} />
                <Route path="/searchbook" element={<SearchBook onsearch={this.searchBook} />} />
                <Route path="/resultbook" element={<SearchResultBook/>} />
                <Route path="/romanticism"  exact element={<Showromanticismbooks show={this.state.booksrom}/>}/>
            </Routes>
          </div>
        </div>
    )
  }
    componentDidMount() {
        this.booksrom();
    }
  searchAuthor = (name) => {
    Service.searchAuthor(name)
        .then((data)=>{

          this.setState({
              author:data.data
          })
      });
  }
    booksrom = () => {
        Service.booksromanticism()
            .then((data)=>{
                this.setState({
                    booksrom:data.data
                })
            });
    }
  searchBook = (name) => {
        Service.searchBook(name)
            .then((data)=>{

                this.setState({
                    book:data.data
                })
            });
    }
}

export default App;

