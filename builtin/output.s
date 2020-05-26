	.section	.data
	.section	.rodata
string0:
	.word	5
	.string	"<< "
	.section	text
	.globl	main
main:
.main:
	addi	sp,sp,-16
	sw	ra,12(sp)
.init_global:
	j	.entry
.entry:
	la	t5,string0
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
	lw	ra,12(sp)
	addi	sp,sp,16
	jr	ra