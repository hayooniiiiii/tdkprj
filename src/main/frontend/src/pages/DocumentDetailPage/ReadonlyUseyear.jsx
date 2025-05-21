import React from 'react';
import {
    Card, CardContent, Typography, Table, TableBody,
    TableCell, TableHead, TableRow
} from '@mui/material';

const ReadonlyUseyear = ({ data }) => {
    const remainingDays = 16 - Number(data?.usedDays || 0);

    return (
        <Card>
            <CardContent>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                    연차 사용 현황
                </Typography>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">기준년도</TableCell>
                            <TableCell align="center">휴가유형</TableCell>
                            <TableCell align="center">이월연차</TableCell>
                            <TableCell align="center">당해월차</TableCell>
                            <TableCell align="center">당해연차</TableCell>
                            <TableCell align="center">사용일수</TableCell>
                            <TableCell align="center">잔여일수</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell align="center">{data?.year ?? '-'}</TableCell>
                            <TableCell align="center">연차휴가</TableCell>
                            <TableCell align="center">{data?.monthAccrued ?? 0}</TableCell>
                            <TableCell align="center">{data?.monthUsed ?? 0}</TableCell>
                            <TableCell align="center">{data?.monthlyBalance ?? 0}</TableCell>
                            <TableCell align="center">{Number(data?.usedDays ?? 0).toFixed(2)}</TableCell>
                            <TableCell align="center">{remainingDays.toFixed(2)}</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </CardContent>
        </Card>
    );
};

export default ReadonlyUseyear;
