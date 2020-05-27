	.section	.data
global0:
	.zero	4
global1:
	.zero	4
global2:
	.zero	4
global3:
	.zero	4
	.section	.rodata
string0:
	.word	3
	.string	"<< "
string1:
	.word	1
	.string	"("
string2:
	.word	2
	.string	") "
string3:
	.word	1
	.string	" "
string4:
	.word	3
	.string	">> "
	.section	text
	.globl	main
main:
.main:
	addi	sp,sp,-272
	sw	ra,268(sp)
.init_global:
	j	.main.entry
.main.entry:
	call	__getInt__
	mv	t5,a0
	sw	t5,264(sp)
	lw	t3,264(sp)
	sw	t3,global0,t5
	sw	t5,260(sp)
	call	__getInt__
	mv	t5,a0
	sw	t5,256(sp)
	lw	t3,256(sp)
	sw	t3,global1,t5
	sw	t5,252(sp)
	call	__getInt__
	mv	t5,a0
	sw	t5,248(sp)
	lw	t3,248(sp)
	sw	t3,global2,t5
	sw	t5,244(sp)
	lw	t3,global1
	sw	t3,240(sp)
	lw	t3,global2
	sw	t3,236(sp)
	lw	t3,240(sp)
	lw	t4,236(sp)
	sub	t5,t3,t4
	sw	t5,232(sp)
	li	t3,1
	sw	t3,228(sp)
	lw	t3,232(sp)
	lw	t4,228(sp)
	sub	t5,t3,t4
	sw	t5,224(sp)
	lw	t3,224(sp)
	sltz	t5,t3
	sw	t5,220(sp)
	lw	t3,220(sp)
	xori	t5,t3,1
	sw	t5,216(sp)
	lw	t3,216(sp)
	beqz	t3,.if.0.end
	j	.if.0.then
.if.0.then:
	la	t5,string0
	sw	t5,212(sp)
	lw	t3,212(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,208(sp)
	j	.if.0.end
.if.0.else:
	j	.if.0.end
.if.0.end:
	lw	t3,global1
	sw	t3,204(sp)
	lw	t3,global2
	sw	t3,200(sp)
	lw	t3,204(sp)
	lw	t4,200(sp)
	sub	t5,t3,t4
	sw	t5,196(sp)
	lw	t3,196(sp)
	sw	t3,global3,t5
	sw	t5,192(sp)
	j	.for.0.condition
.for.0.condition:
	lw	t3,global3
	sw	t3,188(sp)
	lw	t3,global1
	sw	t3,184(sp)
	lw	t3,global2
	sw	t3,180(sp)
	lw	t3,184(sp)
	lw	t4,180(sp)
	add	t5,t3,t4
	sw	t5,176(sp)
	lw	t3,188(sp)
	lw	t4,176(sp)
	sub	t5,t3,t4
	sw	t5,172(sp)
	lw	t3,172(sp)
	sgtz	t5,t3
	sw	t5,168(sp)
	lw	t3,168(sp)
	xori	t5,t3,1
	sw	t5,164(sp)
	lw	t3,164(sp)
	beqz	t3,.for.0.end
	j	.for.0.body
.for.0.body:
	lw	t3,global3
	sw	t3,160(sp)
	li	t3,1
	sw	t3,156(sp)
	lw	t3,156(sp)
	lw	t4,160(sp)
	sub	t5,t3,t4
	sw	t5,152(sp)
	lw	t3,152(sp)
	sgtz	t5,t3
	sw	t5,148(sp)
	lw	t3,148(sp)
	xori	t5,t3,1
	sw	t5,144(sp)
	li	t3,0
	sw	t3,140(sp)
	lw	t3,140(sp)
	mv	t5,t3
	sw	t5,136(sp)
	lw	t3,144(sp)
	beqz	t3,.short.0.end
	j	.short.0.second
.short.0.second:
	lw	t3,global3
	sw	t3,132(sp)
	lw	t3,global0
	sw	t3,128(sp)
	lw	t3,132(sp)
	lw	t4,128(sp)
	sub	t5,t3,t4
	sw	t5,124(sp)
	lw	t3,124(sp)
	sgtz	t5,t3
	sw	t5,120(sp)
	lw	t3,120(sp)
	xori	t5,t3,1
	sw	t5,116(sp)
	lw	t3,116(sp)
	mv	t5,t3
	sw	t5,136(sp)
	j	.short.0.end
.short.0.end:
	lw	t3,136(sp)
	mv	t5,t3
	sw	t5,112(sp)
	lw	t3,112(sp)
	beqz	t3,.if.1.end
	j	.if.1.then
.if.1.then:
	lw	t3,global3
	sw	t3,108(sp)
	lw	t3,global1
	sw	t3,104(sp)
	lw	t3,108(sp)
	lw	t4,104(sp)
	sub	t5,t3,t4
	sw	t5,100(sp)
	lw	t3,100(sp)
	seqz	t5,t3
	sw	t5,96(sp)
	lw	t3,96(sp)
	beqz	t3,.if.2.else
	j	.if.2.then
.if.2.then:
	la	t5,string1
	sw	t5,92(sp)
	lw	t3,92(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,88(sp)
	lw	t3,global3
	sw	t3,84(sp)
	lw	t3,84(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,80(sp)
	lw	t3,80(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,76(sp)
	la	t5,string2
	sw	t5,72(sp)
	lw	t3,72(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,68(sp)
	j	.if.2.end
.if.2.else:
	lw	t3,global3
	sw	t3,64(sp)
	lw	t3,64(sp)
	mv	a0,t3
	call	__printInt__
	mv	t5,a0
	sw	t5,60(sp)
	la	t5,string3
	sw	t5,56(sp)
	lw	t3,56(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,52(sp)
	j	.if.2.end
.if.2.end:
	j	.if.1.end
.if.1.else:
	j	.if.1.end
.if.1.end:
	j	.for.0.step
.for.0.step:
	lw	t3,global3
	sw	t3,48(sp)
	li	t3,1
	sw	t3,44(sp)
	lw	t3,48(sp)
	lw	t4,44(sp)
	add	t5,t3,t4
	sw	t5,40(sp)
	lw	t3,40(sp)
	sw	t3,global3,t5
	sw	t5,36(sp)
	j	.for.0.condition
.for.0.end:
	lw	t3,global1
	sw	t3,32(sp)
	lw	t3,global2
	sw	t3,28(sp)
	lw	t3,32(sp)
	lw	t4,28(sp)
	add	t5,t3,t4
	sw	t5,24(sp)
	lw	t3,global0
	sw	t3,20(sp)
	lw	t3,24(sp)
	lw	t4,20(sp)
	sub	t5,t3,t4
	sw	t5,16(sp)
	lw	t3,16(sp)
	sltz	t5,t3
	sw	t5,12(sp)
	lw	t3,12(sp)
	beqz	t3,.if.3.end
	j	.if.3.then
.if.3.then:
	la	t5,string4
	sw	t5,8(sp)
	lw	t3,8(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,4(sp)
	j	.if.3.end
.if.3.else:
	j	.if.3.end
.if.3.end:
	li	t3,0
	sw	t3,0(sp)
	lw	t3,0(sp)
	mv	a0,t3
	lw	ra,268(sp)
	addi	sp,sp,272
	jr	ra

