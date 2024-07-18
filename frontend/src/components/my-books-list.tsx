"use client";

import { useEffect, useState } from "react";
import { BookType } from "@/types/book-type";
import toast from "react-hot-toast";
import axios from "axios";
import MyLoader from "@/components/loader";
import { useAuth } from "../../context/auth-context";
import PaginationForSearch from "@/components/pagination-for-search";
import BookCard from "@/components/BookCard";

interface PageResponse {
    content: BookType[];
    totalPages: number;
    number: number;
}

export default function MyBooksList() {
    const { token } = useAuth();
    const [result, setResults] = useState<PageResponse | null>(null);
    const [page, setPage] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const getBooks = async () => {
            try {
                setError(null);
                setLoading(true);

                const response = await axios.get(
                    `${process.env.NEXT_PUBLIC_API_BASE_URL}/books/owner?page=${page}`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );

                console.log(response);
                if (response.status === 200) {
                    setResults(response.data);
                }
            } catch (e) {
                setError("Error fetching borrowed books. Try again later.");
                toast.error("Error fetching borrowed books. Try again later.");
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
        setPage(newPage - 1);
    };

    if (loading) return <MyLoader />;
    if (error) return <div className="container mx-auto text-center py-10">{error}</div>;

    return (
        <div className="container mx-auto py-10">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3  gap-6">
                {result && result.content.map((book) => (
                    <BookCard key={book.id} book={book} />
                ))}
            </div>
            {result && (
                <PaginationForSearch
                    totalPages={result.totalPages}
                    currentPage={result.number + 1}
                    onPageChange={handlePageChange}
                />
            )}
        </div>
    );
}
