	.section	.data
	.section	.rodata
string0:
	.word	1
	.string	"s"
string1:
	.word	1
	.string	"y"
string2:
	.word	1
	.string	"b"
	.section	text
	.globl	main
main:
.main:
	addi	sp,sp,-32
	sw	ra,28(sp)
.init_global:
	j	.entry
.entry:
	la	t5,string0
	sw	t5,24(sp)
	lw	t3,24(sp)
	mv	a0, t3
	la	t5,string1
	sw	t5,20(sp)
	lw	t3,20(sp)
	mv	a1, t3
	call	__stringConcatenate__
	mv	t5, a0
	sw	t5,16(sp)
	lw	t3,16(sp)
	mv	a0, t3
	la	t5,string2
	sw	t5,12(sp)
	lw	t3,12(sp)
	mv	a1, t3
	call	__stringConcatenate__
	mv	t5, a0
	sw	t5,8(sp)
	lw	t3,8(sp)
	mv	a0, t3
	call	__print__
	mv	t5, a0
	sw	t5,4(sp)
	li	t3,0
	sw	t3,0(sp)
	lw	t3,0(sp)
	mv	a0, t3
	lw	ra,28(sp)
	addi	sp,sp,32
	jr	ra