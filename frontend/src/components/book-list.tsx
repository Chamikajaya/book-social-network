"use client";

import { useEffect, useState } from "react";
import { BookType } from "@/types/book-type";
import toast from "react-hot-toast";
import axios from "axios";
import MyLoader from "@/components/loader";
import {useAuth} from "../../context/auth-context";


export default function BookList() {

    const { token } = useAuth(); // grabbing the token from the context

    const [books, setBooks] = useState<BookType[]>([]);
    const [page, setPage] = useState<number>(0);

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const getBooks = async () => {
            try {
                setError(null);
                setLoading(true);

                const response = await axios.get(
                    `${process.env.NEXT_PUBLIC_API_BASE_URL}/books?page=${page}`,
                    // send the token in the request headers
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );

                if (response.status === 200) {
                    setBooks(response.data.content);
                }
            } catch (e) {
                setError("Error fetching books. Try again later.");
                toast.error("Error fetching books. Try again later.");
                console.error("Error fetching books", e);
            } finally {
                setLoading(false);
            }
        };

        if (token) {
            getBooks();
        } else {
            setError("You need to be logged in to view books.");
        }
    }, [page, token]);

    const handlePageChange = (newPage: number) => {
        setPage(newPage);
    };

    if (loading) return <MyLoader />;

    if (error) return <div className="container mx-auto text-center py-10">{error}</div>;

    return (
        <div>
            {/* TODO: implement the jsx later, make sure the api hits :) */}
            {books.map((book) => (
                <div key={book.id}>{book.title}</div> // Adjust according to your BookType
            ))}
        </div>
    );
}