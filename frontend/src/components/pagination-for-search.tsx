import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination";

interface PaginationForSearchProps {
    totalPages: number;
    currentPage: number;
    onPageChange: (page: number) => void;
}

export default function PaginationForSearch({
                                                totalPages,
                                                currentPage,
                                                onPageChange,
                                            }: PaginationForSearchProps) {
    const handlePageChange = (page: number) => {
        if (page >= 1 && page <= totalPages) {
            onPageChange(page);
        }
    };

    const renderPageNumbers = () => {
        const pages = [];
        const maxPageNumbers = 3;

        let startPage = Math.max(1, currentPage - Math.floor(maxPageNumbers / 2));
        let endPage = startPage + maxPageNumbers - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = Math.max(1, endPage - maxPageNumbers + 1);
        }

        if (startPage > 1) {
            pages.push(
                <PaginationItem key={1}>
                    <PaginationLink onClick={() => handlePageChange(1)}>1</PaginationLink>
                </PaginationItem>
            );
            if (startPage > 2) {
                pages.push(
                    <PaginationItem key="start-ellipsis">
                        <PaginationEllipsis />
                    </PaginationItem>
                );
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            pages.push(
                <PaginationItem key={i}>
                    <PaginationLink
                        onClick={() => handlePageChange(i)}
                        isActive={currentPage === i}
                    >
                        {i}
                    </PaginationLink>
                </PaginationItem>
            );
        }

        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                pages.push(
                    <PaginationItem key="end-ellipsis">
                        <PaginationEllipsis />
                    </PaginationItem>
                );
            }
            pages.push(
                <PaginationItem key={totalPages}>
                    <PaginationLink onClick={() => handlePageChange(totalPages)}>
                        {totalPages}
                    </PaginationLink>
                </PaginationItem>
            );
        }

        return pages;
    };

    return (
        <Pagination className="mt-10">
            <PaginationContent>
                <PaginationItem>
                    {currentPage > 1 && (
                        <PaginationPrevious onClick={() => handlePageChange(currentPage - 1)} />
                    )}
                </PaginationItem>
                {renderPageNumbers()}
                <PaginationItem>
                    {currentPage < totalPages && (
                        <PaginationNext onClick={() => handlePageChange(currentPage + 1)} />
                    )}
                </PaginationItem>
            </PaginationContent>
        </Pagination>
    );
}