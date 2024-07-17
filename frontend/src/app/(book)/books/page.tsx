import BookList from "@/components/book-list";

export default function BooksPage() {
    // todo: add navbar to layout later
    return (
        <div>
            <BookList />
        </div>
    );
}

// TODO: Implement the navbar component + search bar
// ! Display all books in a grid in /books + implement pagination :) -> Make sure to send the JWT token in the request headers (AUTHORIZATION)  to authenticate the user :)
// * refer 8.40 h in the video -> Bearer 'token'
// Bottom line is that add the token to request headers -  Authorization: `Bearer ${token}`
// first simply get the book titles to display