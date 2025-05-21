import React from 'react';
import {
    Card, CardContent, Typography, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Paper
} from '@mui/material';

const ReadonlyRegister = ({ data }) => {
    const sumUsage = data?.reduce((acc, row) => acc + Number(row.usageDays || 0), 0).toFixed(2);

    return (
        <Card>
            <CardContent>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                    신청 현황
                </Typography>
                <TableContainer component={Paper}>
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell>휴가유형</TableCell>
                                <TableCell>시작일</TableCell>
                                <TableCell>종료일</TableCell>
                                <TableCell>시작시간</TableCell>
                                <TableCell>종료시간</TableCell>
                                <TableCell align="right">사용일수</TableCell>
                                <TableCell>비고</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {data?.map((row, idx) => (
                                <TableRow key={idx}>
                                    <TableCell>{row.vacationType}</TableCell>
                                    <TableCell>{row.startDate}</TableCell>
                                    <TableCell>{row.endDate}</TableCell>
                                    <TableCell>{row.startTime}</TableCell>
                                    <TableCell>{row.endTime}</TableCell>
                                    <TableCell align="right">{Number(row.usageDays).toFixed(2)}</TableCell>
                                    <TableCell>{row.note}</TableCell>
                                </TableRow>
                            ))}
                            <TableRow>
                                <TableCell colSpan={5} align="right"><strong>합계</strong></TableCell>
                                <TableCell align="right"><strong>{sumUsage}</strong></TableCell>
                                <TableCell />
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
};

export default ReadonlyRegister;
